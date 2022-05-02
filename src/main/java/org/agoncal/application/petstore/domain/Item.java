package org.agoncal.application.petstore.domain;

import org.agoncal.application.petstore.constraint.NotEmpty;
import org.agoncal.application.petstore.constraint.Price;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.*;
@Getters
@setters
@ToString
@EqualsAndHashCode
/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Entity
@NamedQueries({
        @NamedQuery(name = Item.FIND_BY_PRODUCT_ID, query = "SELECT i FROM Item i WHERE i.product.id = :productId"),
        @NamedQuery(name = Item.SEARCH, query = "SELECT i FROM Item i WHERE UPPER(i.name) LIKE :keyword OR UPPER(i.product.name) LIKE :keyword ORDER BY i.product.category.name, i.product.name"),
        @NamedQuery(name = Item.FIND_ALL, query = "SELECT i FROM Item i")
})
@XmlRootElement
public class Item {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 30)
    @NotNull
    @Size(min = 1, max = 30)
    private String name;
    @Column(length = 3000)
    private String description;
    @Column(nullable = false)
    @Price
    private Float unitCost;
    @NotEmpty
    private String imagePath;
    @ManyToOne
    @JoinColumn(name = "product_fk", nullable = false)
    @XmlTransient
    private Product product;

    // ======================================
    // =             Constants              =
    // ======================================

    public static final String FIND_BY_PRODUCT_ID = "Item.findByProductId";
    public static final String SEARCH = "Item.search";
    public static final String FIND_ALL = "Item.findAll";

    // ======================================
    // =            Constructors            =
    // ======================================

    public Item() {
    }

    public Item(String name, Float unitCost, String imagePath, Product product, String description) {
        this.name = name;
        this.unitCost = unitCost;
        this.imagePath = imagePath;
        this.product = product;
        this.description = description;
    }
}
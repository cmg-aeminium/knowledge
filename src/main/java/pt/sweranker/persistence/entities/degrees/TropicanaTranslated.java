/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.persistence.entities.degrees;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Carlos Gonçalves
 */
@Entity
@Table(name = "empty")
public class TropicanaTranslated implements Serializable {

    private static final long serialVersionUID = -7950524049133845822L;

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cenas")
    private String cenas;

    @Column(name = "age")
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCenas() {
        return cenas;
    }

    public void setCenas(String cenas) {
        this.cenas = cenas;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}

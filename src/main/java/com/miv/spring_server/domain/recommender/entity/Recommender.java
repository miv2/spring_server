package com.miv.spring_server.domain.recommender.entity;

import com.miv.spring_server.domain.user.entity.User;
import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recommender")
public class Recommender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_recommender_id")
    private Recommender parentRecommender;


    @OneToMany (mappedBy = "parentRecommender", cascade = CascadeType.ALL)
    private List<Recommender> childRecommenders = new ArrayList<>();

    private Integer depth;

    public Recommender() {}

    public Long getId() {
        return id;
    }

    public Recommender getParentRecommender() {
        return parentRecommender;
    }

    public List<Recommender> getChildRecommenders() {
        return childRecommenders;
    }

    public Integer getDepth() {
        return depth;
    }

    public static final class Builder {
        private Recommender parentRecommender;
        private List<Recommender> childRecommenders;
        private Integer depth;

        private Builder() {
        }

        public Builder parentRecommender(Recommender parentRecommender) {
            this.parentRecommender = parentRecommender;
            return this;
        }

        public Builder childRecommenders(List<Recommender> childRecommenders) {
            this.childRecommenders = childRecommenders;
            return this;
        }

        public Builder depth(Integer depth) {
            this.depth = depth;
            return this;
        }

        public Recommender build() {
            Recommender recommender = new Recommender();
            recommender.parentRecommender = this.parentRecommender;
            recommender.childRecommenders = this.childRecommenders;
            recommender.depth = this.depth;

            return recommender;
        }
    }
}

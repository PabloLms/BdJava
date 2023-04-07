package dto;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchases")
public class Purchase {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "total", nullable = false)
  private Double total;

  @Temporal(TemporalType.DATE)
  @Column(name = "date_purchase", nullable = false)
  private Date datePurchase;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Purchase)) {
      return false;
    }
    Purchase purchase = (Purchase) o;
    return getId().equals(purchase.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}

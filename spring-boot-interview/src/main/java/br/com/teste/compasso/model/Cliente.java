package br.com.teste.compasso.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente implements Serializable {
	private static final long serialVersionUID = -7759336206012025424L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@SuppressWarnings("deprecation")
	@ApiModelProperty(value = "Nome do cliente")
	@Column(nullable = false)
	@NotEmpty(message = "O campo nome é obrigatório")
	private String name;
	@ApiModelProperty(value = "Sexo do cliente")
	private String sexo;
	@ApiModelProperty(value = "Data de nascimento")
	private Date dataNascimento;
	@ApiModelProperty(value = "Idade")
	private int idade;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cidade_id", referencedColumnName = "id")
	@ApiModelProperty(value = "Informações sobre a cidade do cliente")
	private Cidade cidade;

}

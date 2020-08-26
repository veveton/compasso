package br.com.teste.compasso.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cidade implements Serializable {

	private static final long serialVersionUID = -8760097378076895184L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ApiModelProperty(value = "Nome da cidade")
	@SuppressWarnings("deprecation")
	@NotEmpty(message = "O campo nome da cidade é obrigatório")
	@Column(nullable = false)
	private String name;
	
	@ApiModelProperty(value = "Estado")
	@SuppressWarnings("deprecation")
	@NotEmpty(message = "O campo estado é obrigatório")
	@Column(nullable = false)
	private String estado;

}

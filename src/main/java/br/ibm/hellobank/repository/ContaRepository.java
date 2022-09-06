package br.ibm.hellobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ibm.hellobank.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{

}

package br.ibm.hellobank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ibm.hellobank.model.Conta;
import br.ibm.hellobank.repository.ContaRepository;

@Service
public class ContaService implements ServiceInterface<Conta>{
	
	@Autowired
	private ContaRepository repository;

	public ContaService() {
	}
	
	@Override
	public Conta create(Conta conta) {
		repository.save(conta);
		return conta;
	}
	
	@Override
	public List<Conta> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Conta findById(Long id) {
		Optional<Conta> obj = repository.findById(id);
		return obj.orElse(null);
	}
	
	@Override
	public boolean update(Conta conta) {
		if (repository.existsById(conta.getId())) {
			repository.save(conta);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean delete(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;			
		}
		return false;
	}
	
	public Float depositar(Long id, Float valor) throws IllegalArgumentException {
		Optional<Conta> obj = repository.findById(id);
		if (obj.isPresent()) {
			Conta conta = obj.get();
			conta.setSaldo(conta.getSaldo() + valor);
			repository.save(conta);
			return conta.getSaldo();
		}
		throw new IllegalArgumentException("Conta não encontrada");
	}
	
	public Float sacar(Long id, Float valor) throws IllegalArgumentException, RuntimeException {
		Optional<Conta> obj = repository.findById(id);
		if (obj.isPresent()) {
			Conta conta = obj.get();
			if (conta.getSaldo() >= valor) {
				conta.setSaldo(conta.getSaldo() - valor);
				repository.save(conta);
				return conta.getSaldo();
			}
			throw new RuntimeException("Saldo insuficiente");
		}
		throw new IllegalArgumentException("Conta não encontrada");
	}
}
package com.fiap.hospitality.client.repository;

import com.fiap.hospitality.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {

    public Client findByCpf(String cpf);

}

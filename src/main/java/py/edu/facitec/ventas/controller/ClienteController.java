package py.edu.facitec.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.ventas.entity.Cliente;
import py.edu.facitec.ventas.service.ClienteService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class ClienteController {
    @Autowired
    private ClienteService ClienteService;
    @QueryMapping
    public List<Cliente> findAllClientes() {
        return ClienteService.findAllClientes();
    }
    @QueryMapping
    public Cliente findClienteById(@Argument("ClienteId") int ClienteId) {
        return ClienteService.findOneCliente(ClienteId);
    }
    @QueryMapping
    public Cliente findOneCliente(@Argument("id") int id) {
        return ClienteService.findOneCliente(id);
    }
    @MutationMapping
    public Cliente createCliente(@Argument("inputCliente") Cliente inputCliente) {
        return ClienteService.saveCliente(inputCliente);
    }
    @MutationMapping
    public Cliente saveCliente(@Argument("dto") Cliente dto) {
        return ClienteService.saveCliente(dto);
    }
    @MutationMapping
    public Cliente updateCliente(@Argument("id") int id, @Argument("inputCliente") Cliente inputCliente) {
        return ClienteService.updateCliente(id, inputCliente);
    }

    @MutationMapping
    public Cliente updateClienteWithId(@Argument("id") int id, @Argument("dto") Cliente dto) {
        return ClienteService.updateCliente(id, dto);
    }

    @MutationMapping
    public Cliente deleteCliente(@Argument("id") int id) {
        return ClienteService.deleteCliente(id);
    }

    @MutationMapping
    public String deleteClienteById(@Argument("ClienteId") int ClienteId) {
        ClienteService.deleteCliente(ClienteId);
        return "Cliente eliminado exitosamente";
    }
    @SubscriptionMapping
    public Flux<Cliente> findAllClientesFlux() {
        return ClienteService.findAllClienteesFlux();
    }
    @SubscriptionMapping
    public Mono<Cliente> findOneClienteMono(@Argument("id") int id) {
        return ClienteService.findOneMono(id);
    }
}
package py.edu.facitec.ventas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.dto.PaginadorDto;
import py.edu.facitec.ventas.entity.Categoria;
import py.edu.facitec.ventas.entity.Cliente;
import py.edu.facitec.ventas.repository.ClienteRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class ClienteService{
    @Autowired
    PaginadorService paginadorService;
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente findOneCliente(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con id " + id + " no existe"));
    }

    public Flux<Cliente> findAllClienteesFlux() {
        return Flux.fromIterable(findAllClientes())
                .delayElements(Duration.ofSeconds(1))
                .take(10);
    }

    public Mono<Cliente> findOneMono(int id) {
        return Mono.justOrEmpty(clienteRepository.findById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Cliente con id " + id + " no existe")));
    }

    public Cliente saveCliente(Cliente dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Cliente no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Cliente cliente = Cliente.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .documento(dto.getDocumento())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .activo(dto.getActivo())
                .fechaRegistro(dto.getFechaRegistro())
                .build();

        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(int id, Cliente dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Cliente no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Cliente Cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con id " + id + " no existe"));

        Cliente.setNombre(dto.getNombre());
        Cliente.setApellido(dto.getApellido());
        Cliente.setDocumento(dto.getDocumento());
        Cliente.setTelefono(dto.getTelefono());
        Cliente.setEmail(dto.getEmail());
        Cliente.setActivo(dto.getActivo());
        Cliente.setFechaRegistro(dto.getFechaRegistro());

        return clienteRepository.save(Cliente);
    }

    public Cliente deleteCliente(int id) {
        Cliente Cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con id " + id + " no existe"));

        clienteRepository.delete(Cliente);
        return Cliente;
    }

    public PaginadorDto<Cliente> findClientesPaginated(int page, int size, String search) {
        return paginadorService.paginarConFiltro(
                (s, pageable) -> {
                    if (s == null || s.trim().isEmpty()) {
                        return clienteRepository.findAll(pageable);
                    }
                    return clienteRepository.findByNombreContainingIgnoreCase(s, pageable);
                },
                search,
                page,
                size
        );
    }

    private void validarCamposObligatorios(Cliente dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (dto.getApellido() == null || dto.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        if (dto.getDocumento() == null || dto.getDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("El documento es obligatorio");
        }
        if (dto.getFechaRegistro() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");
        }
    }
}
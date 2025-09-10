package py.edu.facitec.ventas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.entity.Vendedor;
import py.edu.facitec.ventas.repository.VendedorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class VendedorService {
    @Autowired
    private VendedorRepository vendedorRepository;
    public List<Vendedor> findAllVendedores() {
        return vendedorRepository.findAll();
    }
    public Vendedor findOneVendedor(int id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor con id " + id + " no existe"));
    }
    public Flux<Vendedor> findAllVendedoresFlux() {
        return Flux.fromIterable(findAllVendedores())
                .delayElements(Duration.ofSeconds(1))
                .take(10);
    }
    public Mono<Vendedor> findOneMono(int id) {
        return Mono.justOrEmpty(vendedorRepository.findById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Vendedor con id " + id + " no existe")));
    }
    public Vendedor saveVendedor(Vendedor dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Vendedor no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Vendedor vendedor = Vendedor.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .documento(dto.getDocumento())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .activo(dto.getActivo())
                .fechaNacimiento(dto.getFechaNacimiento())
                .build();

        return vendedorRepository.save(vendedor);
    }

    public Vendedor updateVendedor(int id, Vendedor dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Vendedor no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Vendedor vendedor = vendedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor con id " + id + " no existe"));

        vendedor.setNombre(dto.getNombre());
        vendedor.setApellido(dto.getApellido());
        vendedor.setDocumento(dto.getDocumento());
        vendedor.setTelefono(dto.getTelefono());
        vendedor.setEmail(dto.getEmail());
        vendedor.setActivo(dto.getActivo());
        vendedor.setFechaNacimiento(dto.getFechaNacimiento());

        return vendedorRepository.save(vendedor);
    }

    public Vendedor deleteVendedor(int id) {
        Vendedor vendedor = vendedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor con id " + id + " no existe"));

        vendedorRepository.delete(vendedor);
        return vendedor;
    }
    private void validarCamposObligatorios(Vendedor dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (dto.getApellido() == null || dto.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        if (dto.getDocumento() == null || dto.getDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("El documento es obligatorio");
        }
    }
}
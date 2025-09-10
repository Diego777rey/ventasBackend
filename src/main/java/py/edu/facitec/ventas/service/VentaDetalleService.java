package py.edu.facitec.ventas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.facitec.ventas.entity.VentaDetalle;
import py.edu.facitec.ventas.repository.VentaDetalleRepository;

import java.util.List;

@Service
public class VentaDetalleService {

    @Autowired
    VentaDetalleRepository ventaDetalleRepository;

    public List<VentaDetalle> findByVentaId(Integer ventaId) {
        return ventaDetalleRepository.findByVentaId(ventaId);
    }
}
package icop.parcialpromocionvillalval.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import icop.parcialpromocionvillalval.dto.RequestPrestamoDto;
import icop.parcialpromocionvillalval.dto.ResponsePrestamoDto;
import icop.parcialpromocionvillalval.entity.Prestamo;
import icop.parcialpromocionvillalval.entity.Socio;
import icop.parcialpromocionvillalval.exception.ReglaNegocioException;
import icop.parcialpromocionvillalval.mapper.PrestamoMapper;
import icop.parcialpromocionvillalval.repository.PrestamoRepository;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final SocioService socioService;

    public PrestamoService(PrestamoRepository prestamoRepository, SocioService socioService) {
        this.prestamoRepository = prestamoRepository;
        this.socioService = socioService;
    }


    @Transactional
    public ResponsePrestamoDto crear(RequestPrestamoDto dto) {
    
        Socio socio = socioService.obtenerSocio(dto.getSocioId());

    
        if (prestamoRepository.existsByTituloLibroAndDevueltoFalse(dto.getTituloLibro())) {
            throw new ReglaNegocioException("El libro " + dto.getTituloLibro() + " ya se encuentra prestado");
        }

    
        Prestamo prestamo = PrestamoMapper.toEntity(dto);
        socio.agregarPrestamo(prestamo);

        Prestamo guardado = prestamoRepository.save(prestamo);
        return PrestamoMapper.toDto(guardado);
    }


    @Transactional(readOnly = true)
    public List<ResponsePrestamoDto> listarPorSocio(Long socioId) {
    
        Socio socio = socioService.obtenerSocio(socioId);
        return socio.getPrestamos()
                .stream()
                .map(PrestamoMapper::toDto)
                .toList();
    }
}

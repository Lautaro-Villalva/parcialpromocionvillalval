package icop.parcialpromocionvillalval.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import icop.parcialpromocionvillalval.dto.RequestSocioDto;
import icop.parcialpromocionvillalval.dto.ResponseSocioDto;
import icop.parcialpromocionvillalval.entity.Socio;
import icop.parcialpromocionvillalval.exception.RecursoNoEncontradoException;
import icop.parcialpromocionvillalval.exception.ReglaNegocioException;
import icop.parcialpromocionvillalval.mapper.SocioMapper;
import icop.parcialpromocionvillalval.repository.SocioRepository;

@Service
public class SocioService {

    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    
    @Transactional(readOnly = true)
    public List<ResponseSocioDto> listar() {
        return socioRepository.findAll(Sort.by("id"))
                .stream()
                .map(SocioMapper::toDto)
                .toList();
    }

    
    @Transactional(readOnly = true)
    public ResponseSocioDto buscarPorId(Long id) {
        return SocioMapper.toDto(obtenerSocio(id));
    }

    
    @Transactional
    public ResponseSocioDto crear(RequestSocioDto dto) {
        
        if (socioRepository.existsByDni(dto.getDni())) {
            throw new ReglaNegocioException("Ya existe un socio con el DNI: " + dto.getDni());
        }
        Socio guardado = socioRepository.save(SocioMapper.toEntity(dto));
        return SocioMapper.toDto(guardado);
    }

    
    @Transactional
    public void eliminar(Long id) {
        Socio socio = obtenerSocio(id); 

        
        if (!socio.getPrestamos().isEmpty()) {
            throw new ReglaNegocioException(
                    "No se puede eliminar el socio con id: " + id + " porque tiene préstamos asociados");
        }
        socioRepository.delete(socio);
    }

    
    public Socio obtenerSocio(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Socio no encontrado con id: " + id));
    }
}

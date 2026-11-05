package icop.parcialpromocionvillalval.mapper;

import icop.parcialpromocionvillalval.dto.RequestPrestamoDto;
import icop.parcialpromocionvillalval.dto.ResponsePrestamoDto;
import icop.parcialpromocionvillalval.entity.Prestamo;

public class PrestamoMapper {

    private PrestamoMapper() {
    }

    
    public static Prestamo toEntity(RequestPrestamoDto dto) {
        Prestamo prestamo = new Prestamo();
        prestamo.setTituloLibro(dto.getTituloLibro());
        prestamo.setFechaPrestamo(dto.getFechaPrestamo());
        prestamo.setDevuelto(false); 
        return prestamo;
    }

    public static ResponsePrestamoDto toDto(Prestamo prestamo) {
        return new ResponsePrestamoDto(
                prestamo.getId(),
                prestamo.getTituloLibro(),
                prestamo.getFechaPrestamo(),
                prestamo.isDevuelto(),
                SocioMapper.nombreCompleto(prestamo.getSocio())); 
    }
}

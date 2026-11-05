package icop.parcialpromocionvillalval.mapper;

import icop.parcialpromocionvillalval.dto.RequestSocioDto;
import icop.parcialpromocionvillalval.dto.ResponseSocioDto;
import icop.parcialpromocionvillalval.entity.Socio;


public class SocioMapper {

    private SocioMapper() {
    }

    public static Socio toEntity(RequestSocioDto dto) {
        Socio socio = new Socio();
        socio.setNombre(dto.getNombre());
        socio.setApellido(dto.getApellido());
        socio.setDni(dto.getDni());
        return socio;
    }

    public static ResponseSocioDto toDto(Socio socio) {
        return new ResponseSocioDto(nombreCompleto(socio), socio.getDni());
    }

    public static String nombreCompleto(Socio socio) {
        return socio.getNombre() + " " + socio.getApellido();
    }
}

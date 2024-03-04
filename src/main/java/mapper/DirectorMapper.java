package mapper;

import dto.DirectorDTO;
import entity.Director;

public class DirectorMapper {

    public static DirectorDTO toDTO(Director director) {
        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setDirectorId(director.getDirectorId());
        directorDTO.setName(director.getName());

        return directorDTO;
    }

    public static Director toEntity(DirectorDTO directorDTO) {
        Director director = new Director();
        director.setDirectorId(directorDTO.getDirectorId());
        director.setName(directorDTO.getName());

        return director;
    }
}
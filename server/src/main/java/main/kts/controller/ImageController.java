package main.kts.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.zip.Deflater;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import main.kts.dto.ImageDTO;
import main.kts.helper.ImageMapper;
import main.kts.model.Image;
import main.kts.service.FileService;
import main.kts.service.ImageService;

@RestController
@RequestMapping(value = "/api/image", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {

	@Autowired
	ImageService imageService;

	ImageMapper imageMapper;

	@Autowired
	FileService storageService;

	public ImageController() {
		imageMapper = new ImageMapper();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ImageDTO>> getAllImages() {
		List<Image> images = imageService.findAll();

		return new ResponseEntity<>(toImageDTOList(images), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
		Image image = imageService.findOne(id);
		if (image == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		File fi = new File(image.getRelativePath());
		byte[] fileContent = Files.readAllBytes(fi.toPath());
		return ResponseEntity.status(HttpStatus.OK).body(fileContent);

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Page<ImageDTO>> loadImagePage(Pageable pageable) {
		Page<Image> images = imageService.findAll(pageable);
		if (images == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Page<ImageDTO> imagesDTO = toImageDTOPage(images);
		return new ResponseEntity<>(imagesDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
	@PreAuthorize("hasAnyRole('ADMIN', 'REGISTERED_USER')")
	public ResponseEntity<String> createImage(@RequestBody MultipartFile file) {
		Image image;
		String message = "";
		
		ImageDTO imageDTO = new ImageDTO(file.getOriginalFilename(), "src/main/resources/static/images/"+file.getOriginalFilename());
		if (!this.validateImageDTO(imageDTO))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		try {
			image = imageService.create(imageMapper.toEntity(imageDTO));
			Long id = image.getId();
			image = imageService.update(new Image(id, "file"+id+".jpg", "src/main/resources/static/images/file"+id+".jpg"), image.getId());
			storageService.save(file, image.getId());
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			
			return new ResponseEntity<>(id.toString(), HttpStatus.OK);
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ADMIN', 'REGISTERED_USER')")
	public ResponseEntity<ImageDTO> updateImage(@RequestBody ImageDTO imageDTO, @PathVariable Long id) {
		Image image;
		try {
			image = imageService.update(imageMapper.toEntity(imageDTO), id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(imageMapper.toDto(image), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('ADMIN', 'REGISTERED_USER')")
	public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
		try {
			imageService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private List<ImageDTO> toImageDTOList(List<Image> images) {
		List<ImageDTO> imageDTOS = new ArrayList<>();
		for (Image image : images) {
			imageDTOS.add(imageMapper.toDto(image));
		}
		return imageDTOS;
	}

	private Page<ImageDTO> toImageDTOPage(Page<Image> images) {
		Page<ImageDTO> dtoPage = images.map(new Function<Image, ImageDTO>() {
			@Override
			public ImageDTO apply(Image entity) {
				ImageDTO dto = imageMapper.toDto(entity);
				return dto;
			}
		});
		return dtoPage;
	}

	private boolean validateImageDTO(ImageDTO imageDTO) {
		if (imageDTO.getRelativePath() == null)
			return false;
		if (imageDTO.getName() == null)
			return false;
		try {
			Paths.get(imageDTO.getRelativePath());
		} catch (InvalidPathException | NullPointerException ex) {
			return false;
		}
		return true;
	}
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}
}

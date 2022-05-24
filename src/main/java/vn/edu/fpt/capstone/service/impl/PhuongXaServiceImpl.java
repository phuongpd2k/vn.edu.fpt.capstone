package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.PhuongXaDto;
import vn.edu.fpt.capstone.model.PhuongXaModel;
import vn.edu.fpt.capstone.repository.PhuongXaRepository;
import vn.edu.fpt.capstone.service.PhuongXaService;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class PhuongXaServiceImpl implements PhuongXaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhuongXaServiceImpl.class);
    ModelMapper mapperDTO2Entity = new ModelMapper();
    @Autowired
    PhuongXaRepository phuongXaRepository;
    @Autowired
    ModelMapper modelMapper;

    @PostConstruct
    void postConstruct() {
        mapperDTO2Entity.createTypeMap(PhuongXaDto.class, PhuongXaModel.class)
                .addMappings(m -> {
                });
    }

    @Override
    public PhuongXaDto findById(Long id) {
        PhuongXaDto phuongXaDto = modelMapper.map(phuongXaRepository.findById(id).get(), PhuongXaDto.class);
        return phuongXaDto;
    }

    @Override
    @Cacheable("phuongXa")
    public List<PhuongXaDto> findAll() {
        List<PhuongXaModel> phuongXaModels = phuongXaRepository.findAll();
        if (phuongXaModels == null || phuongXaModels.isEmpty()) {
            return null;
        }
        List<PhuongXaDto> phuongXaDtos = Arrays.asList(modelMapper.map(phuongXaModels, PhuongXaDto[].class));
        return phuongXaDtos;
    }

    @Override
    public boolean isExist(Long id) {
        return phuongXaRepository.existsById(id);
    }

	@Override
	public List<PhuongXaDto> findAllByMaQh(Long maQh) {
		// TODO Auto-generated method stub
				List<PhuongXaModel> phuongXaModels = phuongXaRepository.findAllByMaQh(maQh);
				if (phuongXaModels == null || phuongXaModels.isEmpty()) {
					return null;
				}
				List<PhuongXaDto> phuongXaDtos = Arrays.asList(modelMapper.map(phuongXaModels, PhuongXaDto[].class));
				return phuongXaDtos;
	}

}

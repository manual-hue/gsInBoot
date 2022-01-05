package org.team.gstreet.message.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.team.gstreet.member.entity.Member;
import org.team.gstreet.message.dto.MesseageDTO;
import org.team.gstreet.message.entity.Message;
import org.team.gstreet.message.repository.MesseageRepository;
import org.team.gstreet.saleboard.dto.PageRequestDTO;
import org.team.gstreet.saleboard.dto.PageResponseDTO;
import org.team.gstreet.saleboard.dto.SaleBoardDTO;
import org.team.gstreet.saleboard.entity.SaleBoard;
import org.team.gstreet.saleboard.repository.SaleBoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MesseageServiceImpl implements MesseageService{

    private final ModelMapper modelMapper;
    private final MesseageRepository messeageRepository;

    @Override
    public Long register(MesseageDTO messeageDTO) {
        Message messeage = modelMapper.map(messeageDTO, Message.class);

        messeageRepository.save(messeage);

        log.info("등록완료" + messeage);

        return messeage.getMno();
    }

    @Override
    public MesseageDTO read(Long mno) {

        Optional<Message> result = messeageRepository.findById(mno);

        MesseageDTO dto =  modelMapper.map(result.get(),MesseageDTO.class);

        return dto;
    }

    @Override
    public PageResponseDTO<Message> getListMesseage(PageRequestDTO pageRequestDTO, String mid) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("mno").descending());
        Page<Message> result = messeageRepository.getListSelect(mid,pageable);

        List<MesseageDTO> dtoList = result.get().map(message -> modelMapper.map(message, MesseageDTO.class)).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return new PageResponseDTO(pageRequestDTO, (int) totalCount, dtoList);

    }






}

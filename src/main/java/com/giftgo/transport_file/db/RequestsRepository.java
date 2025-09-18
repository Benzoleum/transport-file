package com.giftgo.transport_file.db;

import com.giftgo.transport_file.dto.IncomingRequestDto;
import org.springframework.data.repository.CrudRepository;

public interface RequestsRepository extends CrudRepository<IncomingRequestDto, Long> {


}

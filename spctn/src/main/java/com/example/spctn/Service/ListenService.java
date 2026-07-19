package com.example.spctn.Service;

import com.example.spctn.Entity.Listen;

public interface ListenService {



	    Long findListenCountBySongId(Long id);

	    Listen save(Listen listen);

	
}

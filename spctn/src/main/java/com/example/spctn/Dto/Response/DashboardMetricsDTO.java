package com.example.spctn.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DashboardMetricsDTO {
	private Long totalSongs;
	
	private Long totalUsers;
	
	private Long totalPlays;
	
	private Long totalLikes;
	
	private Long totalCommets;
}

package cn.unis.api4walo.dto;

import java.util.List;

public class GameDownloadTimesReportDto {

	private Long game_id;
	private String nameForCH;
	private String nameForEN;
	private List<DateAndTimesDto> data;
	
	public GameDownloadTimesReportDto(Long game_id, String nameForCH, String nameForEN, List<DateAndTimesDto> data){
		this.game_id = game_id;
		this.nameForCH = nameForCH;
		this.nameForEN = nameForEN;
		this.data = data;
	}
	
	public Long getGame_id() {
		return game_id;
	}
	public void setGame_id(Long game_id) {
		this.game_id = game_id;
	}
	public List<DateAndTimesDto> getData() {
		return data;
	}
	public void setData(List<DateAndTimesDto> data) {
		this.data = data;
	}

	public String getNameForCH() {
		return nameForCH;
	}

	public void setNameForCH(String nameForCH) {
		this.nameForCH = nameForCH;
	}

	public String getNameForEN() {
		return nameForEN;
	}

	public void setNameForEN(String nameForEN) {
		this.nameForEN = nameForEN;
	}
	
	
}

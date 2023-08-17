package com.snuh.smile.mapper;

import com.snuh.smile.domain.ActivityDetails;
import com.snuh.smile.vo.ActivityDetailSummayVO;
import com.snuh.smile.vo.ActivityDetailVO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ActivityDetailSummaryMapper.class)
public interface ActivityDetailMapper extends GenericMapper<ActivityDetailVO, ActivityDetails.ActivityDetail> {
    ActivityDetailMapper INSTANCE = Mappers.getMapper(ActivityDetailMapper.class);

    ActivityDetails.ActivityDetail toEntity(ActivityDetailVO activityDetailVO);

    //@Mapping(target = "summary", source = "summary", qualifiedByName = "summaryVOList")
   // @Mapping(target = "samples", source = "samples", qualifiedByName = "samplesVOList")
   // @Mapping(target = "laps", source = "laps", qualifiedByName = "lapsVOList")
    ActivityDetailVO toVO(ActivityDetails.ActivityDetail activityDetail);

    ActivityDetailSummayVO toSummaryVO(ActivityDetails.Summarys summarys);
}


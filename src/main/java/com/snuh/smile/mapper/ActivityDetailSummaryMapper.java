package com.snuh.smile.mapper;

import com.snuh.smile.domain.ActivityDetails;
import com.snuh.smile.vo.ActivityDetailSummayVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = ActivityDetailMapper.class)
public interface ActivityDetailSummaryMapper extends GenericMapper<ActivityDetailSummayVO, ActivityDetails.Summarys> {

    ActivityDetailSummaryMapper INSTANCE = Mappers.getMapper(ActivityDetailSummaryMapper.class);

    ActivityDetails.Summarys toEntity(ActivityDetailSummayVO activityDetailSummayVO);

    @Mapping(target = "activityDetails.summary", ignore = true)
    ActivityDetailSummayVO toVO(ActivityDetails.Summarys summarys);

    @Named("summaryVOList")
    default List<ActivityDetailSummayVO> tosummaryVOList(List<ActivityDetails.Summarys> summarysList) {
        return summarysList
                .stream()
                .map(this::toVO)
                .peek(vo -> vo.setActivityDetailVO(null))
                .collect(Collectors.toList());
    }
}
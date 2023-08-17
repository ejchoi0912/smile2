package com.snuh.smile.mapper;

import com.snuh.smile.domain.Activity;
import com.snuh.smile.vo.ActivityVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActivityMapper extends GenericMapper<ActivityVO, Activity>  {
    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

    Activity toEntity(ActivityVO activityVO);
    ActivityVO toVO(Activity.Activities activity);
}

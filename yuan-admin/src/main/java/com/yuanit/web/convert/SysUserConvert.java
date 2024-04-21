package com.yuanit.web.convert;

import com.yuanit.common.convert.BaseConvert;
import com.yuanit.common.convert.CommonMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = CommonMapperConfig.class)
public interface SysUserConvert extends BaseConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);
}
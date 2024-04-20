package com.ruoyi.web.convert;

import com.ruoyi.common.convert.BaseConvert;
import com.ruoyi.common.convert.CommonMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = CommonMapperConfig.class)
public interface SysUserConvert extends BaseConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);
}
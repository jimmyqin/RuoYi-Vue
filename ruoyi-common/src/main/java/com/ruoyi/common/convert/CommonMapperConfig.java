package com.ruoyi.common.convert;


import com.google.common.base.Joiner;
import com.ruoyi.common.enums.BaseEnum;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.JsonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.MapperConfig;

import java.util.Date;
import java.util.Optional;

/**
 * @author qinrongjun
 * @description
 */
@MapperConfig(uses = {BaseEnum.class, CommonConvert.class}, imports = {JsonUtils.class, Optional.class, Date.class, Joiner.class, StringUtils.class, BooleanUtils.class, CollectionUtils.class, DictUtils.class})
public interface CommonMapperConfig { }

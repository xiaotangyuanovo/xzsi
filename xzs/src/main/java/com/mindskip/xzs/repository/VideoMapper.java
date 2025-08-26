package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {
    List<Video> selectAll();
}

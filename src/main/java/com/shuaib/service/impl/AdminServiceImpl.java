package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Admin;
import com.shuaib.mapper.AdminMapper;
import com.shuaib.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {}

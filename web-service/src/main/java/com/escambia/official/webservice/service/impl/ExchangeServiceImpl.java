package com.escambia.official.webservice.service.impl;

import com.escambia.official.webservice.service.ExchangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ExchangeServiceImpl
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@Service
@Transactional(rollbackFor = {Throwable.class, Exception.class})
public class ExchangeServiceImpl implements ExchangeService {

}

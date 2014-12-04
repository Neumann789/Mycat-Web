/*
 * Copyright (c) 2013, OpenCloudDB/MyCAT and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software;Designed and Developed mainly by many Chinese 
 * opensource volunteers. you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License version 2 only, as published by the
 * Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Any questions about this component can be directed to it's project Web address 
 * https://code.google.com/p/opencloudb/.
 *
 */
package org.hx.rainbow.server.sa.server;

import java.security.KeyPair;

import org.hx.rainbow.common.context.RainbowContext;
import org.hx.rainbow.common.core.service.BaseService;
import org.hx.rainbow.common.security.rsa.util.SecurityUtil;
import org.hx.rainbow.common.util.ObjectId;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class RsaService extends BaseService {
	private static final String NAMESPACE = "SARSA";
	private static final String QUERY_COMBOX = "queryCombox";

	public RainbowContext query(RainbowContext context) {
		super.query(context, NAMESPACE);
		return context;
	}
	
	public RainbowContext queryCombox(RainbowContext context) {
		super.query(context, NAMESPACE, QUERY_COMBOX);
		return context;
	}
	
	public RainbowContext queryByPage(RainbowContext context) {
		super.queryByPage(context, NAMESPACE);
		return context;
	}
	
	public RainbowContext insert(RainbowContext context) {
		context.addAttr("guid", new ObjectId().toString());
		
		try {
			SecurityUtil securityUtil = SecurityUtil.getInstance();
			KeyPair keyPair = securityUtil.getKeyPair();
			String modulus = securityUtil.getModulus(keyPair);
			String privateKey = securityUtil.getPrivateExponent(keyPair);
			String publicKey = securityUtil.getPublicExponent(keyPair);
			context.addAttr("modulus", modulus);
			context.addAttr("privateKey", privateKey);
			context.addAttr("publicKey", publicKey);			
		} catch (Exception e) {
			context.setSuccess(false);
			context.setMsg("生成密钥错误,系统异常!");
			return context;
		}
		super.insert(context, NAMESPACE);
		context.getAttr().clear();
		return context;
	}
	public RainbowContext update(RainbowContext context) {
		super.update(context, NAMESPACE);
		context.getAttr().clear();
		return context;
	}
	
	public RainbowContext delete(RainbowContext context) {
		super.delete(context, NAMESPACE);
		context.getAttr().clear();
		return context;
	}
}
/*
 * Copyright 2013-2017 imadcn Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.imadcn.framework.common.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Combination {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		String str[] = { "A", "B", "C", "D", "E", "F", "G" };
		int nCnt = str.length;
		int nBit = 1 << nCnt;
		for (int i = 1; i <= nBit; i++) {
			StringBuilder builder = new StringBuilder();
			for (int j = 0; j < nCnt; j++) {
				if ((1 << j & i) != 0) {
					builder.append(str[j]);
				}
			}
			if (builder.length() > 0) {
				list.add(builder.toString());
			}
		}
		Collections.sort(list);
		for (String s : list) {
			System.out.println(s);
		}
	}

}

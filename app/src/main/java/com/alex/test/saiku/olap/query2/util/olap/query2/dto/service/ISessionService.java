/*  
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.alex.test.saiku.olap.query2.util.olap.query2.dto.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ISessionService {

  Map<String, Object> login(HttpServletRequest req,
                            String username, String password) throws Exception;

  void logout(HttpServletRequest req);

  void authenticate(HttpServletRequest req, String username,
                    String password);

  Map<String, Object> getSession() throws Exception;

  Map<String, Object> getAllSessionObjects();

  void clearSessions(HttpServletRequest req, String username, String password) throws Exception;
}

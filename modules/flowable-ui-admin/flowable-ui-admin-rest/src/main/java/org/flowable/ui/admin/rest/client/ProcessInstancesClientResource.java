/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.flowable.ui.admin.rest.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.flowable.ui.admin.domain.EndpointType;
import org.flowable.ui.admin.domain.ServerConfig;
import org.flowable.ui.admin.service.engine.ProcessInstanceService;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ProcessInstancesClientResource extends AbstractClientResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessInstancesClientResource.class);

    @Autowired
    protected ProcessInstanceService clientService;

    protected ObjectMapper objectMapper = new ObjectMapper();

    /**
     * GET /rest/authenticate -> check if the user is authenticated, and return its login.
     */
    @RequestMapping(value = "/rest/admin/process-instances", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonNode listProcessInstances(@RequestBody ObjectNode bodyNode) {
        LOGGER.debug("REST request to get a list of process instances");

        JsonNode resultNode = null;
        try {
            ServerConfig serverConfig = retrieveServerConfig(EndpointType.PROCESS);
            resultNode = clientService.listProcesInstances(bodyNode, serverConfig);

        } catch (Exception e) {
            LOGGER.error("Error processing process instance list request", e);
            throw new BadRequestException(e.getMessage());
        }

        return resultNode;
    }
}

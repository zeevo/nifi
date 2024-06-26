/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.cluster.coordination.http.endpoints;

import org.apache.nifi.cluster.coordination.http.EndpointResponseMerger;
import org.apache.nifi.cluster.manager.NodeResponse;
import org.apache.nifi.cluster.protocol.NodeIdentifier;
import org.apache.nifi.web.api.entity.ParameterProviderEntity;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ParameterProviderFetchRequestsEndpointMerger extends AbstractSingleEntityEndpoint<ParameterProviderEntity> implements EndpointResponseMerger {
    private static final Pattern PARAMETER_PROVIDER_URI = Pattern.compile("/nifi-api/parameter-providers/[a-f0-9\\-]{36}/parameters/fetch-requests");

    @Override
    public boolean canHandle(final URI uri, final String method) {
        return "PUT".equalsIgnoreCase(method) && PARAMETER_PROVIDER_URI.matcher(uri.getPath()).matches();
    }

    @Override
    protected Class<ParameterProviderEntity> getEntityClass() {
        return ParameterProviderEntity.class;
    }

    @Override
    protected void mergeResponses(final ParameterProviderEntity clientEntity, final Map<NodeIdentifier, ParameterProviderEntity> entityMap, final Set<NodeResponse> successfulResponses,
                                  final Set<NodeResponse> problematicResponses) {
        for (final ParameterProviderEntity entity : entityMap.values()) {
            ParameterProviderMerger.merge(clientEntity, entity);
        }
    }

}

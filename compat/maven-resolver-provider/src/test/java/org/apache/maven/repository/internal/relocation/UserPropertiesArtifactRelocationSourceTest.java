/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.repository.internal.relocation;

import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.resolution.ArtifactDescriptorRequest;
import org.eclipse.aether.resolution.ArtifactDescriptorResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Deprecated
class UserPropertiesArtifactRelocationSourceTest {

    @Test
    void trimsWhitespaceAroundRelocationCoordinates() throws Exception {
        DefaultRepositorySystemSession session = new DefaultRepositorySystemSession(h -> false);
        session.setConfigProperty("maven.relocations.entries", "  org.source:artifact:1 > org.target:artifact:2  ");

        ArtifactDescriptorRequest request = new ArtifactDescriptorRequest();
        request.setArtifact(new DefaultArtifact("org.source:artifact:1"));
        request.setRequestContext("project");
        ArtifactDescriptorResult result = new ArtifactDescriptorResult(request);

        Artifact relocated = new UserPropertiesArtifactRelocationSource().relocatedTarget(session, result, null);

        assertNotNull(relocated);
        assertEquals("org.target", relocated.getGroupId());
        assertEquals("2", relocated.getVersion());
    }
}

/*
 * Copyright 2019 Nokia
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.onap.cvc.csar.cc.sol004;


import org.onap.cli.fw.schema.OnapCommandSchema;
import org.onap.cvc.csar.CSARArchive;
import org.onap.cvc.csar.PnfCSARError.PnfCSARErrorEntryMissing;
import org.onap.cvc.csar.cc.VTPValidateCSARBase;

import java.io.File;
import java.util.Objects;

@OnapCommandSchema(schema = "vtp-validate-csar-r57019.yaml")
public class VTPValidateCSARR57019 extends VTPValidateCSARBase {

    private static final int UNKNOWN_LINE_NUMBER = -1;

    public static class ToscaMetaNotAvailableError extends CSARArchive.CSARErrorInvalidEntryValue {
        public ToscaMetaNotAvailableError() {
            super("",
                    "CSAR Archive",
                    "Unable to find TOSCA.meta file in TOSCA-metadata directory.","");
            this.setCode("0x1000");
        }
    }

    @Override
    protected void validateCSAR(CSARArchive csar) {
        final CSARArchive.Manifest manifest = csar.getManifest();
        final CSARArchive.Manifest.Metadata metadata = manifest.getMetadata();
        final File manifestMfFile = csar.getManifestMfFile();
        if(manifestMfFile!=null) {
            final String fileName = manifestMfFile.getName();

            validateMetadataValue(fileName, metadata.getProviderId(), "pnfd_provider");
            validateMetadataValue(fileName, metadata.getProductName(), "pnfd_name");
            validateMetadataValue(fileName, metadata.getReleaseDateTime(), "pnfd_release_date_time");
            validateMetadataValue(fileName, metadata.getPackageVersion(), "pnfd_archive_version");
        }

    }

    private void validateMetadataValue(String fileName, String providerId, String metadataArgumentName) {
        if (Objects.isNull(providerId)) {
            this.errors.add(new PnfCSARErrorEntryMissing(
                    metadataArgumentName,
                    fileName,
                    UNKNOWN_LINE_NUMBER)
            );
        }
    }

    @Override
    protected String getVnfReqsNo() {
        return "R57019";
    }


}

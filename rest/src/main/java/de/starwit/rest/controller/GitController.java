package de.starwit.rest.controller;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.dto.GitAuthDto;
import de.starwit.generator.config.Constants;
import de.starwit.generator.services.AppSetupService;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.service.impl.AppService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * AppSetup RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/git")
public class GitController {

    final static Logger LOG = LoggerFactory.getLogger(GitController.class);

    @Autowired
    private AppSetupService appSetupService;

    @Autowired
    private AppService appService;

    @Operation(summary = "Creates new App from templates.")
    @PostMapping(value = "/setup-app/{appId}")
    public void generateApp(@PathVariable("appId") Long appId, @Valid @RequestBody GitAuthDto dto)
            throws NotificationException {
        appSetupService.setupAndGenerateApp(appId, dto);
    }

    @Operation(summary = "Gets template description from git repository and updates template definitions in database.")
    @PostMapping(value = "/update-templates/{appTemplateId}")
    public void updateCodeTemplates(@PathVariable("appTemplateId") Long appTemplateId,
            @Valid @RequestBody GitAuthDto dto) throws NotificationException {
        appSetupService.updateTemplates(appTemplateId, dto);
    }

    @GetMapping("/download-app/{appId}")
    public void downloadApp(@PathVariable("appId") Long appId, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);

        App entity = appService.findById(appId);
        if (entity != null && entity.getTargetPath() != null) {
            response.setHeader("Content-Disposition", "attachment;filename=" + entity.getTitle() + ".zip");
            File directory = new File(Constants.TMP_DIR + Constants.FILE_SEP + entity.getTargetPath());
            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                try (ZipOutputStream zippedOut = new ZipOutputStream(response.getOutputStream())) {
                    for (File file : files) {
                        if (!Constants.TEMPLATE_DIR.equals(file.getName())) {
                            addDirToZipArchive(zippedOut, file, null);
                        }
                    }
                    zippedOut.finish();
                } catch (Exception e) {
                    LOG.error("Error Downloading App", e);
                    LOG.error("Error downloading app as ZIP-file", e.getCause());
                    ServletOutputStream sos = response.getOutputStream();
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    sos.print(
                            "{response : {data : { metadata : {message : \"error.downloadapp\", responseCode : \"ERROR\"} } }");
                    sos.flush();
                    sos.close();
                }
            }
        }
    }

    public static void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parrentDirectoryName)
            throws Exception {
        if (fileToZip == null || !fileToZip.exists()) {
            return;
        }

        String zipEntryName = fileToZip.getName();
        if (".git".equals(zipEntryName)) {
            return;
        }

        if (parrentDirectoryName != null && !parrentDirectoryName.isEmpty()) {
            zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
        }

        if (fileToZip.isDirectory()) {
            LOG.info("+{}", zipEntryName);
            for (File file : fileToZip.listFiles()) {
                addDirToZipArchive(zos, file, zipEntryName);
            }
        } else {
            LOG.info("   {}", zipEntryName);
            FileSystemResource resource = new FileSystemResource(fileToZip);
            ZipEntry zipEntry = new ZipEntry(resource.getFilename());
            // Configure the zip entry, the properties of the file
            zipEntry.setSize(resource.contentLength());
            zipEntry.setTime(System.currentTimeMillis());
            zos.putNextEntry(new ZipEntry(zipEntryName));
            StreamUtils.copy(resource.getInputStream(), zos);
            zos.closeEntry();
        }
    }

}

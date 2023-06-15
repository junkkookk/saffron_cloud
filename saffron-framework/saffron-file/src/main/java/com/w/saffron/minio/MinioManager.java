package com.w.saffron.minio;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.w.saffron.constant.FileConstant;
import com.w.saffron.dto.FileBean;
import com.w.saffron.exception.OprException;
import com.w.saffron.utils.DateUtil;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author w
 * @since 2023/3/29
 */
@UtilityClass
@Slf4j
public class MinioManager {


    private final io.minio.MinioClient minioClient = SpringUtil.getBean(io.minio.MinioClient.class);

    private final String MINIO_PREFIX="/saffron_files";

    /**
     * 创建一个桶
     */
    public void createBucket(String bucket) throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    /**
     * 上传一个文件
     */
    public String uploadFile(MultipartFile file,String pre) {
        String previewUrl;
        try {
            String bucket = getBucket();
            String objectName = file.getOriginalFilename();
            if (StrUtil.isEmpty(pre)){
                pre = MINIO_PREFIX +FileConstant.SEPARATOR + DateUtil.getCurrentDate();
            }
            String finalName = pre + FileConstant.SEPARATOR+objectName;
            InputStream stream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .contentType(file.getContentType())
                    .object(finalName)
                    .stream(stream, -1, 10485760).build());

            stream.close();
            previewUrl =finalName;
        }catch (Exception e){
            throw new OprException(e.getMessage());
        }
        return previewUrl;
    }

    private static String getBucket() {
        return SpringUtil.getProperty("spring.minio.bucket");
    }

    @SneakyThrows(Exception.class)
    public String getPreview(String filename){
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs
                .builder()
                .expiry(1, TimeUnit.DAYS)
                .object(filename)
                .method(Method.GET)
                .bucket(getBucket())
                .build());
    }

    @SneakyThrows(Exception.class)
    public static List<FileBean> listFiles(String prefix) {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(getBucket())
                .prefix(prefix)
                .recursive(false)
                .build());
        List<FileBean> fileBeans = new ArrayList<>();
        for (Result<Item> itemResult : results) {
            Item item = itemResult.get();
            FileBean fileBean = new FileBean();
            fileBean.setDir(item.isDir());
            fileBean.setSize(item.size());
            String objectName = item.objectName();
            String[] paths = objectName.split("/");
            fileBean.setName(paths[paths.length-1]);
            fileBean.setFullName(objectName);
            fileBeans.add(fileBean);
        }
        return fileBeans;
    }

    @SneakyThrows
    public void preview(String fullName, OutputStream out) {
        try (out; InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(getBucket())
                        .object(fullName)
                        .build())) {
            IoUtil.copy(stream, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void uploadFile(FileInputStream inputStream, String fileName) {
        try {
            String bucket = getBucket();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .contentType(MediaTypeFactory.getMediaType(fileName).orElse(MediaType.APPLICATION_OCTET_STREAM).toString())
                    .object(fileName)
                    .stream(inputStream, -1, 10485760).build());
        }catch (Exception e){
            throw new OprException(e.getMessage());
        }
    }

    @SneakyThrows
    public String uploadFile(String realPath) {
        String fileName = FileNameUtil.getName(realPath);
        String objectName =MINIO_PREFIX + FileConstant.SEPARATOR + DateUtil.getCurrentDate()+ FileConstant.SEPARATOR + fileName;
        try (FileInputStream inputStream = new FileInputStream(realPath)) {
            MinioManager.uploadFile(inputStream,objectName);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return objectName;
    }
}

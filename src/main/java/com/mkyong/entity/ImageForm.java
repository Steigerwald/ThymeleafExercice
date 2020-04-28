package com.mkyong.entity;

import com.mkyong.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

public class ImageForm extends ImageEntity {

        private String nomImage;
        private String mimeType;
        private boolean newImage = false;
        // Upload file.
        private MultipartFile fileData;

        public ImageForm() {
            this.newImage= true;
        }

        public ImageForm(ImageEntity imageEntity) {

            this.nomImage = imageEntity.getNomImage();
            this.mimeType = imageEntity.getMimeType();
        }

    @Override
    public String getNomImage() {
        return nomImage;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    public boolean isNewImage() {
        return newImage;
    }

    public MultipartFile getFileData() {
            return fileData;
        }

        public void setFileData(MultipartFile fileData) {
            this.fileData = fileData;
        }

    @Override
    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    @Override
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setNewImage(boolean newImage) {
        this.newImage = newImage;
    }
}

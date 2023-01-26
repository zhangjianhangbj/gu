package manage.tool.utils.minio;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 黑白名单库表
 * </p>
 *
 * @author lichao
 * @since 2022-07-27
 */
@Data
@ApiModel(value = "FileInfo对象", description = "文件信息")
public class FileInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("path 文件地址")
  private String path;

  @ApiModelProperty("文件密码")
  private String password;

  private String originalFilename;
  

  @Override
  public String toString() {
    return "FileInfo{" + "path=" + path + ", password=" + password +"}";
  }
}

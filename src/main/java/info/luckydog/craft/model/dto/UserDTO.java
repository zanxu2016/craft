package info.luckydog.craft.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDTO
 *
 * @author eric
 * @since 2019/6/6
 */
@ApiModel(value = "用户数据", description = "用户数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @ApiModelProperty(name = "id", value = "用户Id", dataType = "Long", notes = "修改用户时必传")
    private Long id;
    @ApiModelProperty(name = "name", value = "姓名", dataType = "String", notes = "用于登录系统")
    private String name;
    @ApiModelProperty(name = "pwd", value = "密码", dataType = "String", notes = "用于登录系统")
    private String pwd;
    @ApiModelProperty(name = "age", value = "年龄", dataType = "Integer", notes = "0-150")
    private Integer age;
    @ApiModelProperty(name = "gender", value = "性别", dataType = "Integer", notes = "0-女；1-男", allowableValues = "0,1")
    private Integer gender;
}

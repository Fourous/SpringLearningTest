package SpringReveal;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * @author fourous
 * @date: 2020/4/1
 * @description: FactoryBean扩展容器对象实例化逻辑接口，每次返回都会返回当前后一天日期
 * 注意这里使用了Java 8的Time
 */
public class NextDayDateFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return LocalDate.now().plusDays(1);
    }

    @Override
    public Class getObjectType() {
        return LocalDate.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

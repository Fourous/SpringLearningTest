package SpringReveal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fourous
 * @date: 2020/4/1
 * @description: 服务定位器模式（Service Locator Pattern）用在我们想使用 JNDI 查询定位各种服务的时候。
 * 考虑到为某个服务查找 JNDI 的代价很高，服务定位器模式充分利用了缓存技术。在首次请求某个服
 * 务时，服务定位器在 JNDI 中查找服务，并缓存该服务对象。当再次请求相同的服务时，服务定位器
 * 会在它的缓存中查找，这样可以在很大程度上提高应用程序的性能。
 */
public class ServiceLocatorTest {
    /**
     * 创建服务接口Service
     */
    public interface Service {
        String getName();

        void execute();
    }

    /**
     * 创建服务实体Service1
     */
    public class Service1 implements Service {
        @Override
        public String getName() {
            return "Service1";
        }

        @Override
        public void execute() {
            System.out.println("Executing Service1");
        }
    }

    /**
     * 创建服务实体Service2
     */
    public class Service2 implements Service {
        @Override
        public String getName() {
            return "Service2";
        }

        @Override
        public void execute() {
            System.out.println("Executing Service2");
        }
    }

    /**
     * 为JNDI查询创建InitialContext
     */
    public class InitialContext {
        public Object lookup(String jndiName) {
            if (jndiName.equalsIgnoreCase("SERVICE1")) {
                System.out.println("Looking up and creating a new Service1 object");
                return new Service1();
            } else if (jndiName.equalsIgnoreCase("SERVICE2")) {
                System.out.println("Looking up and creating a new Service2 object");
                return new Service2();
            }
            return null;
        }
    }

    /**
     * 创建缓存Cache
     */
    public class Cache {
        private List<Service> services;

        public Cache() {
            services = new ArrayList<Service>();
        }

        public Service getService(String serviceName) {
            for (Service service : services) {
                if (service.getName().equalsIgnoreCase(serviceName)) {
                    System.out.println("Returning cached  " + service + " object");
                    return service;
                }
            }
            return null;
        }

        public void addService(Service newService) {
            boolean exists = false;
            for (Service service : services) {
                if (service.getName().equalsIgnoreCase(newService.getName())) {
                    exists = true;
                }
            }
            if (!exists) {
                services.add(newService);
            }
        }
    }

    /**
     * 创建服务定位器
     */
    public class ServiceLocator {
        private Cache cache = new Cache();

        public Service getService(String jndiName) {
            Service service = cache.getService(jndiName);

            if (service != null) {
                return service;
            }

            InitialContext context = new InitialContext();
            Service service1 = (Service) context.lookup(jndiName);
            cache.addService(service1);
            return service1;
        }
    }
/**
 * 程序需要分开写调用，这里合在一起，运行结果如下
 * Looking up and creating a new Service1 object
 * Executing Service1
 * Looking up and creating a new Service2 object
 * Executing Service2
 * Returning cached  com.mazaiting.serloc.Service1@15db9742 object
 * Executing Service1
 * Returning cached  com.mazaiting.serloc.Service2@6d06d69c object
 * Executing Service2
 */
//    public static void main(String[] args) {
//        Service service = ServiceLocator.getService("Service1");
//        service.execute();
//        service = ServiceLocator.getService("Service2");
//        service.execute();
//        service = ServiceLocator.getService("Service1");
//        service.execute();
//        service = ServiceLocator.getService("Service2");
//        service.execute();
//    }

}

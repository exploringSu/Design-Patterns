
/*
我们将创建一个简单的用户验证责任链，其中包含多个过滤器来验证用户的不同方面信息。

首先，我们定义一个用户对象 User，其中包含用户名和密码属性：
*/
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}


/*
接下来，我们定义一个过滤器接口 Filter，其中包含处理请求的方法 doFilter()：
*/
interface Filter {
    void doFilter(User user, FilterChain chain);
}

/*
然后，我们实现具体的过滤器类，以进行用户验证。这里我们创建两个过滤器：UsernameFilter 和 PasswordFilter，分别用于验证用户名和密码：
*/
class UsernameFilter implements Filter {
    @Override
    public void doFilter(User user, FilterChain chain) {
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            System.out.println("Username is valid.");
            chain.doFilter(user); // 将请求传递给下一个过滤器
        } else {
            System.out.println("Invalid username.");
        }
    }
}

class PasswordFilter implements Filter {
    @Override
    public void doFilter(User user, FilterChain chain) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            System.out.println("Password is valid.");
            chain.doFilter(user); // 将请求传递给下一个过滤器
        } else {
            System.out.println("Invalid password.");
        }
    }
}

/*
接下来，我们定义一个过滤器链类 FilterChain，用于管理过滤器的执行顺序：
*/
class FilterChain {
    private List<Filter> filters;
    private int index;

    public FilterChain() {
        filters = new ArrayList<>();
        index = 0;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void doFilter(User user) {
        if (index < filters.size()) {
            Filter filter = filters.get(index);
            index++;
            filter.doFilter(user, this);
        }
    }
}

/*
最后，我们创建一个客户端测试类 Client，用于创建过滤器并构建责任链：
*/
public class Client {
    public static void main(String[] args) {
        User user = new User("john", "password");

        // 创建过滤器
        Filter usernameFilter = new UsernameFilter();
        Filter passwordFilter = new PasswordFilter();

        // 构建责任链
        FilterChain chain = new FilterChain();
        chain.addFilter(usernameFilter);
        chain.addFilter(passwordFilter);

        // 执行责任链
        chain.doFilter(user);
    }
}

/*
在上述示例中，我们首先创建了一个 User 对象作为请求。然后，我们创建了两个具体的过滤器 UsernameFilter 和 PasswordFilter，用于验证用户名和密码。
接下来，我们创建了一个 FilterChain 对象，并将过滤器添加到链中。最后，我们调用 chain.doFilter(user) 来执行责任链。
*/



















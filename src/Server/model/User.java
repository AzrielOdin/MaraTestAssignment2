package Server.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

  public class User implements Serializable
  {
    @Serial private static final long serialVersionUID = 1L;
    private String name;
    private static int nextId = 1;
    private int id;

    public static final User adminUser = new User("admin",0);//creating an admin user for testing purposes

    public User(String name)
    {
      this.name = name;
      this.id = nextId++;
    }
    public User (String name, int id)
    {
      this.name = name;
      this.id = id;
    }

    public User()
    {
      // Required for XML decoding;
      // empty because beans require a no-arg constructor
    }


  public String getName()
  {
    return name;
  }

  public int getId()
  {
    return id;
  }

  public void setName(String name)
  {
    if (name == null || name.isEmpty()) //validation , name is not null or empty
      throw new IllegalArgumentException("Name cannot be null or empty");
    this.name = name;
  }

  // needed for XML serialization
  public void setId(int id)
    {
      this.id = id;
    }
  public static void setNextId(int next)
    {
      nextId = next;
    }

//overrides
    @Override public String toString()
  {
    return "User: " + "name: '" + this.getName() + '\'' + " id: " + this.getId() + ' ';
  }

  @Override public boolean equals(Object o)
  {
    if (o == null || getClass() != o.getClass())
      return false;
    User user = (User) o;
    return id == user.id && Objects.equals(name, user.name);
  }

  @Override public int hashCode()
  {
    return Objects.hash(name, id);
  }


}

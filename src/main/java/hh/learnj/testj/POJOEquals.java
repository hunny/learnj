package hh.learnj.testj;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class POJOEquals {

	private String property;
	private Integer index;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("属性", property)
				.append("索引", index).toString();
	}

	@Override
	public boolean equals(Object object) {
		if (null == object) {
			return false;
		}
		if (object == this) {
			return true;
		}
		if (object.getClass() != getClass()) {
			return false;
		}
		POJOEquals eq = (POJOEquals) object;
		return new EqualsBuilder().appendSuper(super.equals(object))
				.append(property, eq.getProperty())
				.append(index, eq.getIndex()).isEquals();
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}

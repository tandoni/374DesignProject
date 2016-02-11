package problem.asm;

import problem.interfaces.IClass;

// to figure out decorated type
public interface IClassVisitor {
	public IClass getBelongedClass();
}

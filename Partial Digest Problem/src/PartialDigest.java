import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


/**
 * Given all pairwise distances between points on a line, reconstruct the position of these points
 *
 */
public class PartialDigest {
	
	ArrayList<Integer> partialDigest;
	Integer totalWidth;
	ArrayList<Integer> positionOfPoints;
	boolean reconstructionSuccessful;

	public PartialDigest(ArrayList<Integer> partialDigest) {
		
		this.partialDigest = partialDigest;
		this.positionOfPoints = new ArrayList<Integer>();
		this.reconstructionSuccessful = false;
		
		totalWidth = Collections.max(this.partialDigest);
		this.partialDigest.remove(totalWidth);
		this.positionOfPoints.add(Integer.valueOf(0));
		this.positionOfPoints.add(totalWidth);
		
		place();
	
	}
	
	private void place() {
		
		if (this.partialDigest.isEmpty()) {
			this.reconstructionSuccessful = true;
			return;
		}
		
		Integer nextMaximumLength = Collections.max(this.partialDigest);
		
		ArrayList<Integer> distancesFromPositionOfPoints = getDistancesFromPositionOfPoints(nextMaximumLength);
		if (this.partialDigest.containsAll(distancesFromPositionOfPoints)) {
			this.positionOfPoints.add(nextMaximumLength);
			this.partialDigest.removeAll(distancesFromPositionOfPoints);
			place();
			this.positionOfPoints.removeIf(s -> s.compareTo(nextMaximumLength) == 0);
			this.partialDigest.addAll(distancesFromPositionOfPoints);
		}
		
		Integer nextMaximumLengthComplement = Integer.valueOf(Math.abs(this.totalWidth.intValue() - nextMaximumLength.intValue()));
		distancesFromPositionOfPoints = getDistancesFromPositionOfPoints(nextMaximumLengthComplement);
		if (this.partialDigest.containsAll(distancesFromPositionOfPoints)) {
			this.positionOfPoints.add(nextMaximumLengthComplement);
			this.partialDigest.removeAll(distancesFromPositionOfPoints);
			place();
			this.positionOfPoints.removeIf(s -> s.compareTo(nextMaximumLengthComplement) == 0);
			this.partialDigest.addAll(distancesFromPositionOfPoints);
		}
		
		return;
		
	}
	
	private ArrayList<Integer> getDistancesFromPositionOfPoints(Integer nextMaximumLength) {
		
		ArrayList<Integer> distancesFromPositionOfPoints = new ArrayList<Integer>();
		
		Iterator<Integer> positionOfPointsIterator = this.positionOfPoints.iterator();
		Integer nextPoint = null, nextDistance = null;
		while (positionOfPointsIterator.hasNext()) {
			nextPoint = positionOfPointsIterator.next();
			nextDistance = Integer.valueOf(Math.abs(nextPoint.intValue() -nextMaximumLength.intValue()));
			if (!distancesFromPositionOfPoints.contains(nextDistance)) {
				distancesFromPositionOfPoints.add(nextDistance);
			}
		}
		
		return distancesFromPositionOfPoints;
		
	}

	public Collection<Integer> getPositionOfPoints() throws Exception {
		if (isReconstructionSuccessful()) {
			return Collections.unmodifiableCollection(positionOfPoints);
		} else {
			throw new Exception("Reconstruction was not successful!!!");
		}
	}

	public boolean isReconstructionSuccessful() {
		return reconstructionSuccessful;
	}
	
}

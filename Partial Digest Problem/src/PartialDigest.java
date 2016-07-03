import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


/**
 * Given all pairwise distances between points on a line, reconstruct the position of these points
 *
 */
public class PartialDigest {
	private	Integer totalWidth;
	private	ArrayList<Integer> reconstructedPositionOfPoints;
	private	boolean reconstructionSuccessful;

	public PartialDigest(ArrayList<Integer> partialDigest) {
		
		ArrayList<Integer> positionOfPoints = new ArrayList<Integer>();
		this.reconstructedPositionOfPoints = null;
		this.reconstructionSuccessful = false;
		
		totalWidth = Collections.max(partialDigest);
		partialDigest.removeIf(s -> s.compareTo(totalWidth) == 0);
		positionOfPoints.add(Integer.valueOf(0));
		positionOfPoints.add(totalWidth);
		
		place(partialDigest, positionOfPoints);
	
	}
	
	private void place(ArrayList<Integer> partialDigest, ArrayList<Integer> positionOfPoints) {
		
		if (partialDigest.isEmpty()) {
			this.reconstructedPositionOfPoints = positionOfPoints;
			this.reconstructionSuccessful = true;
			return;
		}
		
		Integer nextMaximumLength = Collections.max(partialDigest);
		ArrayList<Integer> distancesFromPositionOfPoints = getDistancesFromPositionOfPoints(nextMaximumLength, positionOfPoints);
		if (partialDigest.containsAll(distancesFromPositionOfPoints)) {
			positionOfPoints.add(nextMaximumLength);
			partialDigest.removeAll(distancesFromPositionOfPoints);
			place(partialDigest, positionOfPoints);
			if (!reconstructionSuccessful) {
				positionOfPoints.removeIf(s -> s.compareTo(nextMaximumLength) == 0);
				partialDigest.addAll(distancesFromPositionOfPoints);
			}
		}
		
		Integer nextMaximumLengthComplement = Integer.valueOf(Math.abs(this.totalWidth.intValue() - nextMaximumLength.intValue()));
		distancesFromPositionOfPoints = getDistancesFromPositionOfPoints(nextMaximumLengthComplement, positionOfPoints);
		if (partialDigest.containsAll(distancesFromPositionOfPoints)) {
			positionOfPoints.add(nextMaximumLengthComplement);
			partialDigest.removeAll(distancesFromPositionOfPoints);
			place(partialDigest, positionOfPoints);
			if (!reconstructionSuccessful) {
				positionOfPoints.removeIf(s -> s.compareTo(nextMaximumLengthComplement) == 0);
				partialDigest.addAll(distancesFromPositionOfPoints);
			}
		}
		
		return;
		
	}
	
	private ArrayList<Integer> getDistancesFromPositionOfPoints(Integer nextMaximumLength, ArrayList<Integer> positionOfPoints) {
		
		ArrayList<Integer> distancesFromPositionOfPoints = new ArrayList<Integer>();
		
		Iterator<Integer> positionOfPointsIterator = positionOfPoints.iterator();
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
			return Collections.unmodifiableCollection(this.reconstructedPositionOfPoints);
		} else {
			throw new Exception("Reconstruction was not successful!!!");
		}
	}

	public boolean isReconstructionSuccessful() {
		return this.reconstructionSuccessful;
	}
	
}
